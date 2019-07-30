package bg.sofia.uni.fmi.mjt.git;

import Branch.Branch;
import Files.File;

import java.util.*;

public class Repository {
    private Map<String, Branch> branchList = new HashMap<>();
    private Branch currentBranch;

    public Repository() {
        currentBranch = new Branch("master");
        branchList.put(currentBranch.getName(), currentBranch);
    }

    public Result add(String... files) {
        // is logic ok?
        List<File> tempStage = new ArrayList<>();
        for (String fileName : files) {
            if (checkIfFileAlreadyExistIn(fileName, currentBranch.getStage().getFilesStagedForCommit())
            || checkIfFileAlreadyExistIn(fileName, currentBranch.getDrive().getFilesInDrive())) {
                return new Result("'" + fileName + "' already exists", false);
            }
            tempStage.add(new File(fileName));
        }
        currentBranch.getStage().getFilesStagedForCommit().addAll(tempStage);

        return new Result("added " + getFormattedDateString(files) + "to stage", true);
    }

    public Result remove(String... files) {
        List<File> tempStage = new ArrayList<>();
        for (String fileName : files) {
            // exist in stage or in drive
            if (!(checkIfFileAlreadyExistIn(fileName, currentBranch.getStage().getFilesStagedForCommit())
                    || checkIfFileAlreadyExistIn(fileName, currentBranch.getDrive().getFilesInDrive()))) {
                return new Result("'" + fileName + "' did not match any files", false);
            }
            tempStage.add(new File(fileName));
        }
        currentBranch.getStage().getFilesForRemoval().addAll(tempStage);

        return new Result("added " + getFormattedDateString(files) + "for removal", true);
    }

    public Result commit(String message) {
        currentBranch.addCommit(new Commit(message, currentBranch.getStage()));

        List<File> tempStageForCommit = currentBranch.getStage().getFilesStagedForCommit();
        List<File> tempStageForRemoval = currentBranch.getStage().getFilesForRemoval();

        boolean containsAll = tempStageForRemoval.containsAll(tempStageForCommit);
        if(tempStageForCommit.size() == 0 && tempStageForRemoval.size() == 0) {
            return new Result("nothing to commit, working tree clean", false);
        }
        if(tempStageForCommit.size() == tempStageForRemoval.size() && containsAll) {
            return new Result("nothing to commit, working tree clean", false);
        }

        currentBranch.getDrive().getFilesInDrive().addAll(currentBranch.getStage().getFilesStagedForCommit());
        currentBranch.getDrive().getFilesInDrive().removeAll(currentBranch.getStage().getFilesForRemoval());

        currentBranch.clearStage();

        return new Result(tempStageForCommit.size() + tempStageForRemoval.size() + " files changed", true);
    }

    public Commit getHead() {
        if(currentBranch.getCurrentCommit() == null) {
            return null;
        }
        return currentBranch.getCurrentCommit();
    }

    public Result log() {
        // refactor this print!!!!!!!!!!!
        if(currentBranch.getCurrentCommit() == null) {
            return new Result("branch " + currentBranch.getName() + " does not have any commits yet", false);
        }
        StringBuilder mess = new StringBuilder();
        int counter = 0;
        for (Commit commit: currentBranch.getCommits()) {
            if(counter == currentBranch.getCommits().size() - 1) {
                mess.insert(0, "commit " + commit.getHash() + "\nDate: " + commit.getDate() + "\n\n\t" + commit.getMessage());
            } else {
                counter++;
                mess.insert(0, "\n\ncommit " + commit.getHash() + "\nDate: " + commit.getDate() + "\n\n\t" + commit.getMessage());
            }

        }
        return new Result(mess.toString(), true);
    }

    public String getBranch() {
        return currentBranch.getName();
    }

    public Result createBranch(String name) {
        if(branchList.containsKey(name)) {
            return new Result("branch " + name + " already exists", false);
        }
        Branch newBranch = new Branch(currentBranch, name);
        branchList.put(name, newBranch);
        return new Result("created branch " + name, true);
    }

    public Result checkoutBranch(String name) {
        if(!branchList.containsKey(name)) {
            return new Result("branch " + name + " does not exist", false);
        }
        currentBranch = branchList.get(name);
        return new Result("switched to branch " + name, true);
    }

   public Result checkoutCommit(String hash) {
       for (Commit commitHash: currentBranch.getCommits()) {
           if(commitHash.getHash().equals(hash)) {
               // algorithm for reverting stage files
               boolean flag = false;

               for (Iterator<Commit> it = currentBranch.getCommits().iterator(); it.hasNext();)
               {
                   Commit commit = it.next();
                   if(commit.getHash().equals(hash)) {
                       currentBranch.setCurrentCommit(commit);
                       flag = true;
                       continue;
                   }
                   if(flag) {
                       // reversed logic of commit, add all removed, and remove all added
                       //addAndReplaceDuplicates(currentBranch.getStage().getFilesStagedForCommit(), currentBranch.getDrive().getFilesInDrive());
                       currentBranch.getDrive().getFilesInDrive().addAll(commit.getCommitStage().getFilesForRemoval());
                       currentBranch.getDrive().getFilesInDrive().removeAll(commit.getCommitStage().getFilesStagedForCommit());
                       // if we don't remove the iterator, but remove form commits.remove(commit), iterator won't notice it
                       it.remove();
                   }
               }
               return new Result("HEAD is now at " + currentBranch.getCurrentCommit().getHash(), true);
           }
       }
       return new Result("commit " + hash + " does not exist", false);
    }

    private boolean checkIfFileAlreadyExistIn(String fileName, Collection coll) {
        File tempFile = new File(fileName);
        return coll.contains(tempFile);

    }

    private StringBuilder getFormattedDateString(String[] files) {
        // need rework!!!!!!! Not good formatting
        StringBuilder formattedString = new StringBuilder();
        if(files.length == 1) {
            return formattedString.append(files[0] + " ");
        }

        int counter = 0;
        for (String file : files) {
            if(counter == files.length - 1) {
                formattedString.append(file + " ");
            } else {
                counter++;
                formattedString.append(file + ", ");
            }
        }
        return formattedString;
    }

    private void addAndReplaceDuplicates(List<File> drive, List<File> stage) {
        // if files are updated for example
        // reversed logic
        for (Iterator it = drive.iterator(); it.hasNext();) {
            File tempFile = (File)it.next();
            if(stage.contains(tempFile)) {
               drive.remove(tempFile);
            }
        }
        drive.addAll(stage);
    }
}