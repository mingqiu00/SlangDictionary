package vn.edu.hcmus.student.sv19127568.slangdict;

/**
 * vn.edu.hcmus.student.sv19127568.slangdict
 * Created by Thu Nguyen
 * Date 12/30/2021 - 11:02 PM
 * Description: Slang model
 */
public class Slang {
    private String slang;
    private String meaning;

    public Slang(String slang, String meaning) {
        this.slang = slang;
        this.meaning = meaning;
    }

    public String getSlang() {
        return slang;
    }

    public void setSlang(String slang) {
        this.slang = slang;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    @Override
    public String toString() {
        return slang;
    }
}
