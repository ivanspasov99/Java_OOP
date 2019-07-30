package bg.sofia.uni.fmi.mjt.git;

import Branch.Branch;
import Drives.Stage;
import Files.File;

import java.util.*;

public class Repository {
    private Map<String, Branch> branchList = new HashMap<>();
    private Branch currentBranch;

    public Repository() {
        currentBranch = new Branch("Master");
        branchList.put(currentBranch.getName(), currentBranch);
    }

    public Result add(String... files) {
        // is logic ok?
        List<File> tempStage = new ArrayList<>();
        for (String fileName : files) {
            if (checkIfFileAlreadyExistIn(fileName, currentBranch.getStage().getFilesStagedForCommit())) {
                return new Result(fileName + " already exists", false);
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
                return new Result(fileName + " did not match any files", false);
            }
            tempStage.add(new File(fileName));
        }
        currentBranch.getStage().getFilesForRemoval().addAll(tempStage);

        return new Result("added " + getFormattedDateString(files) + "for removal", true);
    }

    public Result commit(String message) {
        List<File> tempStageForCommit = currentBranch.getStage().getFilesStagedForCommit();
        List<File> tempStageForRemoval = currentBranch.getStage().getFilesForRemoval();

        boolean containsAll = tempStageForRemoval.containsAll(tempStageForCommit);

        if((tempStageForRemoval.size() == 0 && tempStageForCommit.size() == 0)
                || (tempStageForRemoval.size() == tempStageForCommit.size() )) {
            return new Result("nothing to commit, working tree clean", false);
        }
            addAndReplaceDuplicates(currentBranch.getDrive().getFilesInDrive(), currentBranch.getStage().getFilesStagedForCommit());
            currentBranch.getDrive().getFilesInDrive().removeAll(currentBranch.getStage().getFilesForRemoval());

            currentBranch.clearStage();

            return new Result(tempStageForCommit.size() + tempStageForRemoval.size() + " files changed", true);


    }

    private boolean checkIfFileAlreadyExistIn(String fileName, Collection coll) {
        File tempFile = new File(fileName);
        return coll.contains(tempFile);

    }

    private StringBuilder getFormattedDateString(String[] files) {
        StringBuilder formattedString = new StringBuilder();
        for (String file : files) {
            formattedString.append(file + ", ");
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