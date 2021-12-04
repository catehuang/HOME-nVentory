package services;

import dataaccess.CategoryDB;
import dataaccess.ItemDB;
import dataaccess.UserDB;
import java.util.List;
import models.*;

public class InventoryService {

    public Item get(Integer itemID) throws Exception {
        ItemDB itemsDB = new ItemDB();
        Item item = itemsDB.get(itemID);
        return item;
    }

    public List<Item> getAll(String email) throws Exception {
        ItemDB itemsDB = new ItemDB();
        List<Item> items = itemsDB.getAll(email);
        return items;
    }

    public void insert(int categoryID, String itemName, String string_price, String email) throws Exception {
        Item item = new Item();
        ItemDB itemDB = new ItemDB();
        UserDB userDB = new UserDB();
        CategoryDB categoriesDB = new CategoryDB();

        //item.setItemId(itemID);
        item.setCategory(categoriesDB.get(categoryID));

        if (itemName == null || itemName.equals("")) {
            throw new Exception("invalid_input");
        } else {
            item.setItemName(itemName);
        }

        if (string_price == null || string_price.equals("")) {
            throw new Exception("invalid_input");
        } else {
            double price = Double.parseDouble(string_price);
            if (price <= 0 || price >= 10000) {
                throw new Exception("invalid_input");
            }
            item.setPrice(price);
        }

        item.setOwner(userDB.get(email));
        itemDB.insert(item);
    }

    public void update(Integer itemID, int categoryID, String itemName, String string_price, String email) throws Exception {
        ItemDB itemDB = new ItemDB();
        UserDB userDB = new UserDB();
        CategoryDB categoriesDB = new CategoryDB();

        Item item = itemDB.get(itemID);
        User owner = item.getOwner();

            if (!owner.getEmail().equals(email)) {
            throw new Exception("not_belong_to_you");
        } else {
            //item.setItemId(itemID);
            item.setCategory(categoriesDB.get(categoryID));

            if (itemName == null || itemName.equals("")) {
                throw new Exception("invalid_input");
            } else {
                item.setItemName(itemName);
            }

            if (string_price == null || string_price.equals("")) {
                throw new Exception("invalid_input");
            } else {
                double price = Double.parseDouble(string_price);
                if (price <= 0 || price >= 10000) {
                    throw new Exception("invalid_input");
                }
                item.setPrice(price);
            }

            itemDB.update(item);
        }
    }

    public void delete(Integer itemID, User user) throws Exception {
        ItemDB itemDB = new ItemDB();
      
        if (itemDB.get(itemID).getOwner().equals(user)) {
            Item item = itemDB.get(itemID);
            itemDB.delete(item);
        } else {
            throw new Exception("not_belong_to_you");
        }
    }
}
