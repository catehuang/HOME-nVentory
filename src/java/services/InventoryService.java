package services;

import dataaccess.CategoriesDB;
import dataaccess.ItemsDB;
import dataaccess.UserDB;
import java.util.List;
import models.Items;
import models.Users;

public class InventoryService {

    public Items get(Integer itemID) throws Exception {
        ItemsDB itemsDB = new ItemsDB();
        Items item = itemsDB.get(itemID);
        return item;
    }

    public List<Items> getAll(String username) throws Exception {
        ItemsDB itemsDB = new ItemsDB();
        List<Items> notes = itemsDB.getAll(username);
        return notes;
    }

    public void insert(int categoryID, String itemName, String string_price, String username) throws Exception {
        Items item = new Items();
        ItemsDB itemDB = new ItemsDB();
        UserDB userDB = new UserDB();
        CategoriesDB categoriesDB = new CategoriesDB();

        item.setCategory(categoriesDB.get(categoryID));

        if (itemName == null || itemName.equals("")) {
            throw new Exception("invalid_input");
        } else {
            item.setItemName(itemName);
        }

        if (string_price == null || string_price.equals("")) {
            throw new Exception("invalid_input");
        } 
        else 
        {
            double price = Double.parseDouble(string_price);
            if (price <= 0 || price >= 10000)
            {
                throw new Exception("invalid_input");
            }
            item.setPrice(price);
        }

        item.setOwner(userDB.get(username));
        itemDB.insert(item);
    }
/*
    public void update(Integer itemID, String itemName, double price, String owner) throws Exception {
        ItemsDB itemDB = new ItemsDB();
        Items item = itemDB.get(itemID);
        item.setItemName(itemName);
        item.setPrice(price);

        itemDB.update(item);
    }
*/
    public void delete(Integer itemID, String username) throws Exception {
        ItemsDB itemDB = new ItemsDB();

        if (itemDB.get(itemID).getOwner().getUsername().equals(username)) {
            Items item = itemDB.get(itemID);
            itemDB.delete(item);
        } else {
            throw new Exception("not_belong_to_you");
        }
    }
}
