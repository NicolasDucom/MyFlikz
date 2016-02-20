package com.nicolasdu.MyFlikz.factory;

import com.nicolasdu.MyFlikz.model.Show;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicolas on 2/17/2016.
 */
public class ShowFactory {
    public static List<Show> getShowList() {
        List<Show> showList = new ArrayList<>();
        showList.add(new Show("The walking Dead","A Show About Zombies"));
            showList.add(new Show("The walking Dead","A Show About Zombies"));
            showList.add(new Show("The walking Dead","A Show About Zombies"));
            showList.add(new Show("The walking Dead","A Show About Zombies"));
            showList.add(new Show("The walking Dead","A Show About Zombies"));
            showList.add(new Show("The walking Dead","A Show About Zombies"));
            showList.add(new Show("The walking Dead","A Show About Zombies"));

            return showList;
    }
}
