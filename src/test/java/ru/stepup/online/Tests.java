package ru.stepup.online;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Tests {

    @Test
    @DisplayName("Создание по имени")
    public void createAccount(){
        Account account = Account.ofName("Vadim");
    }
    @Test
    @DisplayName("Установка корректного имени")
    public void setCorrectName(){
        String newName = "Petya";
        Account account = Account.ofName("Vadim");
        account.setName(newName);
        Assertions.assertEquals(account.getName(),newName);
    }
    @Test
    @DisplayName("Установка некорректного имени")
    public void setNoCorrectName(){
        String newName1 = "";
        String newName2 = null;
        Account account = Account.ofName("Vadim");
        Assertions.assertThrows(IllegalArgumentException.class,()->account.setName(newName1));
        Assertions.assertThrows(IllegalArgumentException.class,()->account.setName(newName2));
    }

    @Test
    @DisplayName("Мапа количества валют инкапсулирована")
    public void mapIsEncapsulation(){
        Account account = Account.ofName("Vadim");
        account.setQuantityCurrency(Currency.RUB, 45);
        account.setQuantityCurrency(Currency.EUR, 12);
        Assertions.assertTrue(account.getMapCurrency()!=account.getMapCurrency());
    }
    @Test
    @DisplayName("Корректный откат")
    public void undoCorrect(){
        Account account = Account.ofName("Vadim");
        account.setQuantityCurrency(Currency.RUB, 0);
        account.setQuantityCurrency(Currency.RUB, 45);
        account.setName("Petya");
        account.setName("Petya");
        account.setQuantityCurrency(Currency.EUR, 1000000);
        account.setQuantityCurrency(Currency.EUR, 12000000);
        account.setQuantityCurrency(Currency.EUR, 45);

        account.undo();
        Assertions.assertEquals(12000000,account.getMapCurrency().get(Currency.EUR));
        account.undo();
        account.undo();
        account.undo();
        account.undo();
        account.undo();

        Assertions.assertEquals(account.getName(),"Vadim");
        Assertions.assertEquals(account.getMapCurrency().get(Currency.EUR),null);
        Assertions.assertEquals(account.getMapCurrency().get(Currency.RUB),0);

    }

    @Test
    @DisplayName("Откат недоступен")
    public void undoNoCorrect(){
        Account account = Account.ofName("Vadim");
        Assertions.assertThrows(IllegalArgumentException.class,()->account.undo());
    }


    @Test
    @DisplayName("Корректный сохранение и восстановление копии")
    public void saveCorrect(){
        Account account = Account.ofName("Vadim");
        SaveAccountRep saveAccountRep = new SaveAccountRep();
        account.setQuantityCurrency(Currency.RUB, 0);
        account.setQuantityCurrency(Currency.RUB, 45);

        saveAccountRep.addSave(account.save());

        account.setName("Petya");
        account.setQuantityCurrency(Currency.EUR, 15);
        account.setQuantityCurrency(Currency.RUB, 10);

        account.load(saveAccountRep.getSave(0));

        Assertions.assertEquals(account.getName(),"Vadim");
        Assertions.assertEquals(account.getMapCurrency().get(Currency.EUR),null);
        Assertions.assertEquals(account.getMapCurrency().get(Currency.RUB),45);

    }

}
