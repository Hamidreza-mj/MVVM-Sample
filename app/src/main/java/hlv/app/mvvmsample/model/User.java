package hlv.app.mvvmsample.model;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

@Keep
public class User {

    private int id;
    private String name;
    private int age;
    private boolean isMale;
    private String image;
    private String uniqueID;

    public User() {
    }

    public User(int id, String name, String image, int age, boolean isMale, String uniqueID) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.age = age;
        this.isMale = isMale;
        this.uniqueID = uniqueID;
    }

    public User(int id, String name, int age, boolean isMale) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.isMale = isMale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getUniqueID(), user.getUniqueID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUniqueID());
    }

}
