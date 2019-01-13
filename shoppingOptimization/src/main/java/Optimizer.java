import java.util.ArrayList;
import java.util.List;

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
}
