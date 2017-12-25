package in.codepeaker.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by github.com/codepeaker on 18/12/17.
 */

public class BakingModel implements Parcelable {

    /**
     * id : 1
     * name : Nutella Pie
     * ingredients : [{"quantity":2,"measure":"CUP","ingredient":"Graham Cracker crumbs"},{"quantity":6,"measure":"TBLSP","ingredient":"unsalted butter, melted"},{"quantity":0.5,"measure":"CUP","ingredient":"granulated sugar"},{"quantity":1.5,"measure":"TSP","ingredient":"salt"},{"quantity":5,"measure":"TBLSP","ingredient":"vanilla"},{"quantity":1,"measure":"K","ingredient":"Nutella or other chocolate-hazelnut spread"},{"quantity":500,"measure":"G","ingredient":"Mascapone Cheese(room temperature)"},{"quantity":1,"measure":"CUP","ingredient":"heavy cream(cold)"},{"quantity":4,"measure":"OZ","ingredient":"cream cheese(softened)"}]
     * step : [{"id":0,"shortDescription":"Recipe Introduction","description":"Recipe Introduction","videoURL":"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4","thumbnailURL":""},{"id":1,"shortDescription":"Starting prep","description":"1. Preheat the oven to 350°F. Butter a 9\" deep dish pie pan.","videoURL":"","thumbnailURL":""},{"id":2,"shortDescription":"Prep the cookie crust.","description":"2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.","videoURL":"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4","thumbnailURL":""},{"id":3,"shortDescription":"Press the crust into baking form.","description":"3. Press the cookie crumb mixture into the prepared pie pan and bake for 12 minutes. Let crust cool to room temperature.","videoURL":"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9cb_4-press-crumbs-in-pie-plate-creampie/4-press-crumbs-in-pie-plate-creampie.mp4","thumbnailURL":""},{"id":4,"shortDescription":"Start filling prep","description":"4. Beat together the nutella, mascarpone, 1 teaspoon of salt, and 1 tablespoon of vanilla on medium speed in a stand mixer or high speed with a hand mixer until fluffy.","videoURL":"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd97a_1-mix-marscapone-nutella-creampie/1-mix-marscapone-nutella-creampie.mp4","thumbnailURL":""},{"id":5,"shortDescription":"Finish filling prep","description":"5. Beat the cream cheese and 50 grams (1/4 cup) of sugar on medium speed in a stand mixer or high speed with a hand mixer for 3 minutes. Decrease the speed to medium-low and gradually add in the cold cream. Add in 2 teaspoons of vanilla and beat until stiff peaks form.","videoURL":"","thumbnailURL":"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda20_7-add-cream-mix-creampie/7-add-cream-mix-creampie.mp4"},{"id":6,"shortDescription":"Finishing Steps","description":"6. Pour the filling into the prepared crust and smooth the top. Spread the whipped cream over the filling. Refrigerate the pie for at least 2 hours. Then it's ready to serve!","videoURL":"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda45_9-add-mixed-nutella-to-crust-creampie/9-add-mixed-nutella-to-crust-creampie.mp4","thumbnailURL":""}]
     * servings : 8
     * image :w
     */

    private int id;
    private String name;
    private int servings;
    private String image;
    private ArrayList<IngredientsBean> ingredients;
    private ArrayList<StepsBean> steps;

    public BakingModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        servings = in.readInt();
        image = in.readString();
    }

    public static final Creator<BakingModel> CREATOR = new Creator<BakingModel>() {
        @Override
        public BakingModel createFromParcel(Parcel in) {
            return new BakingModel(in);
        }

        @Override
        public BakingModel[] newArray(int size) {
            return new BakingModel[size];
        }
    };

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

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<IngredientsBean> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<IngredientsBean> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<StepsBean> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<StepsBean> steps) {
        this.steps = steps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeInt(servings);
        parcel.writeString(image);
    }

    public static class IngredientsBean implements Parcelable{
        /**
         * quantity : 2
         * measure : CUP
         * ingredient : Graham Cracker crumbs
         */

        private double quantity;
        private String measure;
        private String ingredient;

        public IngredientsBean(Parcel in) {
            quantity = in.readDouble();
            measure = in.readString();
            ingredient = in.readString();
        }

        public static final Creator<IngredientsBean> CREATOR = new Creator<IngredientsBean>() {
            @Override
            public IngredientsBean createFromParcel(Parcel in) {
                return new IngredientsBean(in);
            }

            @Override
            public IngredientsBean[] newArray(int size) {
                return new IngredientsBean[size];
            }
        };

        public double getQuantity() {
            return quantity;
        }

        public void setQuantity(double quantity) {
            this.quantity = quantity;
        }

        public String getMeasure() {
            return measure;
        }

        public void setMeasure(String measure) {
            this.measure = measure;
        }

        public String getIngredient() {
            return ingredient;
        }

        public void setIngredient(String ingredient) {
            this.ingredient = ingredient;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeDouble(quantity);
            parcel.writeString(measure);
            parcel.writeString(ingredient);
        }
    }

    public static class StepsBean implements Parcelable {
        /**
         * id : 0
         * shortDescription : Recipe Introduction
         * description : Recipe Introduction
         * videoURL : https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4
         * thumbnailURL :
         */

        private int id;
        private String shortDescription;
        private String description;
        private String videoURL;
        private String thumbnailURL;

        protected StepsBean(Parcel in) {
            id = in.readInt();
            shortDescription = in.readString();
            description = in.readString();
            videoURL = in.readString();
            thumbnailURL = in.readString();
        }

        public static final Creator<StepsBean> CREATOR = new Creator<StepsBean>() {
            @Override
            public StepsBean createFromParcel(Parcel in) {
                return new StepsBean(in);
            }

            @Override
            public StepsBean[] newArray(int size) {
                return new StepsBean[size];
            }
        };

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getShortDescription() {
            return shortDescription;
        }

        public void setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getVideoURL() {
            return videoURL;
        }

        public void setVideoURL(String videoURL) {
            this.videoURL = videoURL;
        }

        public String getThumbnailURL() {
            return thumbnailURL;
        }

        public void setThumbnailURL(String thumbnailURL) {
            this.thumbnailURL = thumbnailURL;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(id);
            parcel.writeString(shortDescription);
            parcel.writeString(description);
            parcel.writeString(videoURL);
            parcel.writeString(thumbnailURL);
        }
    }
}
