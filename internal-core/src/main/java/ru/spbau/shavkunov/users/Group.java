package ru.spbau.shavkunov.users;

import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import java.net.URL;

/**
 * A vk group wrap.
 * See https://vk.com/dev/fields_groups.
 */
@Entity
public class Group extends User {
    public Group(@NotNull String name, @NotNull String ID, @NotNull URL photoURL, @NotNull String userLink) {
        this.photo = photoURL;
        this.name = name;
        this.ID = ID;
        this.link = userLink;
    }

    @Override
    public int getID() {
        return -Integer.parseInt(ID);
    }
}
