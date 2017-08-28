package ru.spbau.shavkunov.users;

import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.net.URL;

/**
 * Class describes two kinds of users: community or single person.
 */
@Entity
public abstract class User {
    protected @NotNull String link;
    protected @NotNull String name;

    @Id
    protected @NotNull String ID;
    protected @NotNull URL photo;

    public abstract int getID();

    public String getLink() {
        return link;
    }

    public URL getPhoto() {
        return photo;
    }

    public String getName() {
        return name;
    }
}
