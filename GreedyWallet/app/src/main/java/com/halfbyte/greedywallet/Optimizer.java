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
                double iPriceToQuality = item.getPopularityRank() * item.getPrice();
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

//    public List<Item> optimizer(List<Item> itemList, double budget) throws Exception {
//        List<Item> optimizedItemList = optimizer(itemList);
//        Map<ItemGroup, Item> currentOptimalOfItemGroups = new HashMap<ItemGroup, Item> ();
//        Map<ItemGroup, Item> subOptimalOfItemGroups = new HashMap<ItemGroup, Item> ();
//        optimizedItemList.forEach( i -> currentOptimalOfItemGroups.put(i.group, i) );
//        optimizedItemList.forEach( i -> subOptimalOfItemGroups.put(i.group, i) );
//        //while pricesum is higher than the budget
//        for ( double priceSum = currentOptimalOfItemGroups.values().stream().mapToDouble(i -> i.price).sum(); budget < priceSum; ){
//            //get cheaper suboptimal item for every itemgroup
//            for( ItemGroup itemGroup: currentOptimalOfItemGroups.keySet()){
//                List<Item> itemsInGroup = database.getItems(itemGroup.getValue());
//                Item item = currentOptimalOfItemGroups.get(itemGroup);
//                //find the suboptimal
//                itemsInGroup.stream()
//                        .filter( i-> i.price < item.price)
//                        .max( Comparator.comparingDouble( this::getPriceToQuality ) )
//                        .ifPresent( i -> subOptimalOfItemGroups.put( i.group, i ) );
//            }
//            //then swap the item with least quality reduction
//            try {
//                ItemGroup subOptimalItemGroup = subOptimalOfItemGroups.keySet().stream()
//                        .min(Comparator.comparingDouble(k -> getPriceToQuality(currentOptimalOfItemGroups.get(k)) - getPriceToQuality(subOptimalOfItemGroups.get(k))))
//                        .get();
//                currentOptimalOfItemGroups.put( subOptimalItemGroup, subOptimalOfItemGroups.get(subOptimalItemGroup) );
//            }catch (Exception e){
//                throw new Exception( "Could not found optimized item list" );
//            }
//        }
//        return new ArrayList<Item>(currentOptimalOfItemGroups.values());
//    }

//    private double getPriceToQuality(Item item){
//        return item.qualityPoint / item.price;
//    }

    private static List<Item> getItemsInGroup(String category){
        List<Item> temp = new ArrayList<>();
        for(Item i: DatabaseManager.getInstance().items){
            if(i.getCategory().equals(category))
                temp.add(i);
        }
        return temp;
    }
}
