import java.util.*;

public class Optimizer {

    //gets an item list.
    //returns a optimized version of list.
    public List<Item> optimizer(List<Item> itemList){
        List<Item> optimizedItemList = new ArrayList<Item>();
        //for each item in list
        for(Item item: itemList) {
            //get quality grade
            double priceToQuality = item.qualityPoint / item.price;
            //then check it's group
            ItemGroup group = item.group;
            //then lookup into the groups items and find the item with higher qualityPoint
            List<Item> itemsInGroup = database.getItems(group.getValue());
            Item betterItem = null;
            for(Item i: itemsInGroup){
                double iPriceToQuality = item.qualityPoint / item.price;
                if( priceToQuality < iPriceToQuality ){
                    betterItem = i;
                    break;
                }
            }
            //add it to optimized item list
            if(betterItem != null)
                optimizedItemList.add(betterItem);
            else
                optimizedItemList.add(item);
        }
        return optimizedItemList;
    }

    public List<Item> optimizer(List<Item> itemList, double budget) throws Exception {
        List<Item> optimizedItemList = optimizer(itemList);
        Map<ItemGroup, Item> currentOptimalOfItemGroups = new HashMap<ItemGroup, Item> ();
        Map<ItemGroup, Item> subOptimalOfItemGroups = new HashMap<ItemGroup, Item> ();
        optimizedItemList.forEach( i -> currentOptimalOfItemGroups.put(i.group, i) );
        optimizedItemList.forEach( i -> subOptimalOfItemGroups.put(i.group, i) );
        //while pricesum is higher than the budget
        for ( double priceSum = currentOptimalOfItemGroups.values().stream().mapToDouble(i -> i.price).sum(); budget < priceSum; ){
            //get cheaper suboptimal item for every itemgroup
            for( ItemGroup itemGroup: currentOptimalOfItemGroups.keySet()){
                List<Item> itemsInGroup = database.getItems(itemGroup.getValue());
                Item item = currentOptimalOfItemGroups.get(itemGroup);
                //find the suboptimal
                itemsInGroup.stream()
                        .filter( i-> i.price < item.price)
                        .max( Comparator.comparingDouble( this::getPriceToQuality ) )
                        .ifPresent( i -> subOptimalOfItemGroups.put( i.group, i ) );
            }
            //then swap the item with least quality reduction
            try {
                ItemGroup subOptimalItemGroup = subOptimalOfItemGroups.keySet().stream()
                        .min(Comparator.comparingDouble(k -> getPriceToQuality(currentOptimalOfItemGroups.get(k)) - getPriceToQuality(subOptimalOfItemGroups.get(k))))
                        .get();
                currentOptimalOfItemGroups.put( subOptimalItemGroup, subOptimalOfItemGroups.get(subOptimalItemGroup) );
            }catch (Exception e){
                throw new Exception( "Could not found optimized item list" );
            }
        }
        return new ArrayList<Item>(currentOptimalOfItemGroups.values());
    }

    private double getPriceToQuality(Item item){
        return item.qualityPoint / item.price;
    }
}
