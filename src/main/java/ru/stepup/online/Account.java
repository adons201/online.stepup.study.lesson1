package ru.stepup.online;
import java.util.*;

public class Account {

    private String name;
    private Map<Currency, Integer> mapCurrency = new HashMap<>();
    private Deque<Change> changes = new ArrayDeque<>();

    private Account(String name)  {
        this.setName(name);
    }

    public static Account ofName(String name)  {
        return new Account(name);
    }

    public void setName(String name)  {
        if (name==null||name.isBlank()) throw new IllegalArgumentException("name must not be empty");
        String oldS = this.name;
        changes.push(x->x.name = oldS);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Map<Currency, Integer> getMapCurrency() {
        return new HashMap<>(mapCurrency);
    }

    public void setQuantityCurrency(Currency currency, int quantity) {
        if (currency==null) throw new IllegalArgumentException("currency must not be empty");
        if (quantity < 0) throw new IllegalArgumentException("quantity must be positive");
        Integer oldI =  mapCurrency.get(currency);
        changes.push(x->x.mapCurrency.put(currency, oldI));
        mapCurrency.put(currency, quantity);
    }

    public void undo()  {
        if (changes.isEmpty()||changes.size()==1) throw new IllegalArgumentException("no changes");
        changes.pop().make(this);
    }


    public SaveAccount save(){
        return new SaveAccount(this.name, this.mapCurrency);
    }

    public void load(Save save){
        this.name = ((SaveAccount) save).getSaveName();
        this.mapCurrency = ((SaveAccount) save).getSaveMapCurrency();
    }


    private static class SaveAccount implements Save {
        final private String saveName;
        final private Map<Currency, Integer> saveMapCurrency;

        public SaveAccount(String saveName, Map<Currency, Integer> saveMapCurrency) {
            this.saveName = saveName;
            this.saveMapCurrency = new HashMap<>(saveMapCurrency);
        }

        public String getSaveName() {
            return saveName;
        }

        public Map<Currency, Integer> getSaveMapCurrency() {
            return new HashMap<>(saveMapCurrency);
        }
    }
}
