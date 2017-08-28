package ru.spbau.shavkunov.users;

import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import java.net.URL;

/**
 * A single vk user wrap.
 * See https://vk.com/dev/fields.
 */
@Entity
public class Person extends User {
    private @NotNull String surname;

    public Person(@NotNull String name, @NotNull String surname,
                  @NotNull String ID, @NotNull URL url, @NotNull String userLink) {
        this.name = name;
        this.surname = surname;
        this.ID = ID;
        this.photo = url;
        this.link = userLink;
    }

    @Override
    public @NotNull String getName() {
        return name + " " + surname;
    }

    @Override
    public int getID() {
        return Integer.parseInt(ID);
    }
}
