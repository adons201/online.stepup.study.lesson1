package ru.stepup.online;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Account {

    private String name;
    private Map<Currency, Integer> mapCurrency = new HashMap<>();
    private List<Change> changes = new ArrayList<>();
    private List<Change> save = new ArrayList<>();

    private Account(String name)  {
        this.setName(name);
    }

    public static Account ofName(String name)  {
        return new Account(name);
    }

    public void setName(String name)  {
        if (name==null||name.isBlank()) throw new IllegalArgumentException("name must not be empty");
        String oldS = this.name;
        String newS = name;
        changes.add(x->x.name = oldS);
        save.add(x->x.setName(newS));
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
        Integer newI =  quantity;
        changes.add(x->x.mapCurrency.put(currency, oldI));
        save.add(x->x.setQuantityCurrency(currency, newI));
        mapCurrency.put(currency, quantity);
    }

    public void undo()  {
        if (changes.isEmpty()||changes.size()==1) throw new IllegalArgumentException("no changes");
        changes.get(changes.size()-1).make(this);
        changes.remove(changes.size()-1);
    }


    public List<Change> save(){
      return new ArrayList<>(save);
    }

    public void load(SaveAccount saveAccount){
        mapCurrency = new HashMap<>();
        save = new ArrayList<>();
        changes = new ArrayList<>();
        for (Change change: saveAccount.getSaveAccount()){
            change.make(this);
        }
    }

}
