package com.halfbyte.greedywallet;

import com.halfbyte.greedywallet.models.Item;

import java.util.*;

public class Optimizer {

    //gets an item list.
    //returns a optimized version of list.
    public static List<Item> optimizer(List<String> groupList){
        List<Item> optimizedItemList = new ArrayList<Item>();
        for(String group: groupList) {
            List<Item> itemList = getItemsInGroup(group);
            //for each item in list
            Item betterItem = null;
            double bestpriceToPopularity = Integer.MAX_VALUE;
            for (Item item : itemList) {
                double iPriceToQuality = getPriceToQuality(item);
                if (bestpriceToPopularity > iPriceToQuality) {
                    bestpriceToPopularity = iPriceToQuality;
                    betterItem = item;
                }
            }
            //add it to optimized item list
            if (betterItem != null)
                optimizedItemList.add(betterItem);
        }
        return optimizedItemList;
    }

    public static List<Item> optimizer(List<String> groupList, double budget) throws Exception {
        List<Item> optimizedItemList = optimizer(groupList);
        Map<String, Item> currentOptimalOfItemGroups = new HashMap<String, Item> ();
        Map<String, Item> subOptimalOfItemGroups = new HashMap<String, Item> ();
        optimizedItemList.forEach( i -> subOptimalOfItemGroups.put(i.getCategory(), i) );
        currentOptimalOfItemGroups.putAll(subOptimalOfItemGroups);
        //while pricesum is higher than the budget
        for ( double priceSum = currentOptimalOfItemGroups.values().stream().mapToDouble(i -> i.getPrice()).sum(); budget < priceSum; priceSum = currentOptimalOfItemGroups.values().stream().mapToDouble(i -> i.getPrice()).sum()){
            //get cheaper suboptimal item for every itemgroup
            for( String itemGroup: currentOptimalOfItemGroups.keySet()){
                List<Item> itemsInGroup = getItemsInGroup(itemGroup);
                Item item = currentOptimalOfItemGroups.get(itemGroup);
                //find the suboptimal
                itemsInGroup.stream()
                        .filter( i-> i.getPrice() < item.getPrice())
                        .max( Comparator.comparingDouble( Optimizer::getPriceToQuality ) )
                        .ifPresent( i -> subOptimalOfItemGroups.put( i.getCategory(), i ) );
            }
            //then swap the item with least quality reduction
            try {
                String subOptimalItemGroup = subOptimalOfItemGroups.keySet().stream()
                        .min(Comparator.comparingDouble(k -> getPriceToQuality(currentOptimalOfItemGroups.get(k)) - getPriceToQuality(subOptimalOfItemGroups.get(k))))
                        .get();
                currentOptimalOfItemGroups.put( subOptimalItemGroup, subOptimalOfItemGroups.get(subOptimalItemGroup) );
            }catch (Exception e){
                throw new Exception( "Could not found optimized item list" );
            }
        }
        return new ArrayList<Item>(currentOptimalOfItemGroups.values());
    }

    private static double getPriceToQuality(Item item){
        return item.getPopularityRank() * item.getPrice();
    }

    private static List<Item> getItemsInGroup(String category){
        List<Item> temp = new ArrayList<>();
        for(Item i: DatabaseManager.getInstance().items){
            if(i.getCategory().equals(category))
                temp.add(i);
        }
        return temp;
    }
}
