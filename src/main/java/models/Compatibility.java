package models;

import java.util.ArrayList;

public class Compatibility {
    public static boolean checkConditionsOfCreatingSeasonBouquet(ArrayList<SeasonFlower> seasonFlowers){
        for(SeasonFlower flower:seasonFlowers){
            if(flower.getBouquetID()!=0){
                return false;
            }
        }
        int minFreshness = seasonFlowers.get(0).getFreshnessLevel();
        for(int i=1;i<seasonFlowers.size();i++){
            int newFreshness=seasonFlowers.get(i).getFreshnessLevel();
            if(minFreshness>newFreshness) {
                minFreshness = newFreshness;
            }
        }
        int maxFreshness =seasonFlowers.get(0).getFreshnessLevel();
        for(int i=1;i<seasonFlowers.size();i++){
            int newFreshness=seasonFlowers.get(i).getFreshnessLevel();
            if(maxFreshness<newFreshness) {
                maxFreshness = newFreshness;
            }
        }
        return maxFreshness - minFreshness <= 2;
    }
    public static boolean checkConditionsOfCreatingTropicalBouquet(ArrayList<TropicalFlower> tropicalFlowers){
        int compatible=0,nonCompatible=0;
        for(TropicalFlower flower:tropicalFlowers){
            if(flower.getBouquetID()!=0){
                return false;
            }
            if(flower.isCompatibleWithOthers()){
                compatible++;
            }
            else{
                nonCompatible++;
            }
        }
        if(compatible!=tropicalFlowers.size() && nonCompatible!=tropicalFlowers.size()){
            return false;
        }
        int minFreshness = tropicalFlowers.get(0).getFreshnessLevel();
        for(int i=1;i<tropicalFlowers.size();i++){
            int newFreshness=tropicalFlowers.get(i).getFreshnessLevel();
            if(minFreshness>newFreshness) {
                minFreshness = newFreshness;
            }
        }
        int maxFreshness =tropicalFlowers.get(0).getFreshnessLevel();
        for(int i=1;i<tropicalFlowers.size();i++){
            int newFreshness=tropicalFlowers.get(i).getFreshnessLevel();
            if(maxFreshness<newFreshness) {
                maxFreshness = newFreshness;
            }
        }

        return maxFreshness - minFreshness <= 2;
    }
}
