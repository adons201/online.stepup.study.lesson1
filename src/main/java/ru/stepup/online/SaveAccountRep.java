package ru.stepup.online;

import java.util.*;

public final class SaveAccountRep {

    final private List<Save> saves;

    public SaveAccountRep() {
        saves = new ArrayList<>();
    }

    public void addSave(Save save){
        saves.add(save);
    }

    public Save getSave(Integer index){
        return saves.get(index);
    }
}
