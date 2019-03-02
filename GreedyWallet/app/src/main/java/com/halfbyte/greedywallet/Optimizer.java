package com.halfbyte.greedywallet;

import com.halfbyte.greedywallet.models.Item;

import java.util.*;
import java.util.stream.Collectors;

public class Optimizer {

    //gets an item list.
    //returns a optimized version of list.
    public static List<Item> optimizer(List<String> inputItemList){
        List<Item> optimizedItemList = new ArrayList<Item>();
        for(String item: inputItemList) {
            List<Item> itemList = getItemGroup(item);
            //find item with highest quality
            Item betterItem = null;
            double bestpriceToPopularity = Integer.MAX_VALUE;
            for (Item i : itemList) {
                double iPriceToQuality = getPriceToQuality(i);
                if (bestpriceToPopularity > iPriceToQuality) {
                    bestpriceToPopularity = iPriceToQuality;
                    betterItem = i;
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
        Map<String, Item> subOptimalOfItemGroups = new HashMap<String, Item> ();
        optimizedItemList.forEach( i -> subOptimalOfItemGroups.put(i.getIsim(), i) );
        Map<String, Item> currentOptimalOfItemGroups = new HashMap<String, Item> (subOptimalOfItemGroups);
        //while pricesum is higher than the budget
        double priceSum = currentOptimalOfItemGroups.values().stream().mapToDouble(i -> i.getPrice()).sum();
        while ( budget < priceSum ){
            //get cheaper suboptimal item for every group
            for( String inputItem: currentOptimalOfItemGroups.keySet()){
                List<Item> itemsInGroup = getItemGroup(inputItem);
                Item item = currentOptimalOfItemGroups.get(inputItem);
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
            priceSum = currentOptimalOfItemGroups.values().stream().mapToDouble(i -> i.getPrice()).sum();
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

    //gets item name, returns items similar to it
    private static List<Item> getItemGroup(String itemName){
        List<Item> items = new ArrayList<>();
        HashMap<String, Integer> frequencies = new HashMap<>();
        //find frequency of each category
        for(Item i: DatabaseManager.getInstance().items){
            if(i.getIsim().toLowerCase().contains(itemName.toLowerCase())) {
                items.add(i);
                if( frequencies.containsKey( i.getSubCategory() ) ){
                    frequencies.put(i.getSubCategory(), frequencies.get(i.getSubCategory()) + 1);
                } else {
                    frequencies.put(i.getSubCategory(), 1);
                }
            }
        }
        //find category with highest frequency
        int maxFreq = 0;
        String maxFreqCategory = "";
        for( Map.Entry<String, Integer> e: frequencies.entrySet() ){
            if( maxFreq < e.getValue() ){
                maxFreq = e.getValue();
                maxFreqCategory = e.getKey();
            }
        }
        //return items in high frequency category
        final String fmaxFreqCategory = maxFreqCategory;
        return items.stream().filter(i -> i.getSubCategory().equals(fmaxFreqCategory)).collect(Collectors.toList());
    }
}
