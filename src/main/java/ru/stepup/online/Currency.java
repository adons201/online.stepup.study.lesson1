package ru.stepup.online;

public enum Currency {

    USD("Доллар США"),
    RUB("Рубль"),
    EUR("Евро");

    String title;


    Currency(String title) {
        this.title = title;
    }
}
