package nasretdinovir.example.com.Services;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemListWrapper<T> {

    @SerializedName("items")
    private List<T> items_;

    public ItemListWrapper(List<T> items) {
        items_ = items;
    }

    public List<T> getItems() {
        return items_;
    }
}
