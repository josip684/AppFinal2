package com.example.josip.appfinal2.Others;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TheoryModel {

    private String _title;
    private String _subtitle;
    @SerializedName("cast")
    private List<Cast> castList;
    private String story;

    private String question;
    private String correctAnswer;
    private String layoutID;

    private String choiceA;
    private String choiceB;
    private String choiceC;

    private String tv1;
    private String tv2;
    private String tv3;
    private String pt1;
    private String pt2;

    public String getPt1() {
        return pt1;
    }

    public String getPt2() {
        return pt2;
    }

    public String getTv1() {
        return tv1;
    }

    public String getTv2() {
        return tv2;
    }

    public String getTv3() {
        return tv3;
    }

    public String getChoiceA() {
        return choiceA;
    }

    public String getChoiceB() {
        return choiceB;
    }

    public String getChoiceC() {
        return choiceC;
    }

    public String getLayoutID() {
        return layoutID;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String get_title() {
        return _title;
    }


    public String get_subtitle() {
        return _subtitle;
    }

    public List<Cast> getCastList() {
        return castList;
    }

    public void setCastList(List<Cast> castList) {
        this.castList = castList;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public static class Cast {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
