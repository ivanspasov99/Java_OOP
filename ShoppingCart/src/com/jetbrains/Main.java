package com.jetbrains;

import Interfaces.Item;
import Items.Apple;
import Items.Chocolate;
import ShoppingCart.ListShoppingCart;
import ShoppingCart.MapShoppingCart;

import java.util.Collection;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {
        Apple apple = new Apple();
        //Apple apple1 = new Apple("Ivan", "asd", 5);
        //Apple apple2 = new Apple("Ivan", "asd", 5);
        Apple apple3 = new Apple();
        Apple apple4 = new Apple();
        //Apple apple5 = new Apple("Ivan", "asd", 5);
        Chocolate co = new Chocolate();
        Chocolate co1 = new Chocolate();
        Chocolate co2 = new Chocolate();
        Chocolate co3 = new Chocolate();

        /*ListShoppingCart cart = new ListShoppingCart();

        cart.addItem(apple);
        cart.addItem(apple1);
        cart.addItem(apple2);
        cart.addItem(apple3);
        cart.addItem(apple4);
        cart.addItem(apple5);
        cart.addItem(co);
        cart.addItem(co1);
        cart.addItem(co2);
        cart.addItem(co3);
        cart.addItem(co3);
        cart.addItem(co3);
        Collection<Item> tempCart = cart.getSortedItems();
        System.out.println(cart.getTotal());
        cart.removeItem(new Apple("Ivan", "asd", 5));
        Collection<Item> tempCart2 = cart.getUniqueItems();
        System.out.println(cart.getTotal());*/

        MapShoppingCart cart = new MapShoppingCart();
        cart.addItem(apple3);
        cart.addItem(apple3);
        cart.addItem(apple3);
        cart.addItem(co3);
        cart.addItem(co3);
        cart.addItem(co3);
        cart.addItem(co3);

        Collection<Item> crazyShort = cart.getSortedItems();
        System.out.println("zdr");
    }
}
