package vn.edu.hcmus.student.sv19127568.slangdict.models;

/**
 * vn.edu.hcmus.student.sv19127568.slangdict
 * Created by Thu Nguyen
 * Date 12/30/2021 - 11:02 PM
 * Description: Slang model
 */
public class Slang {
    private final String slang;
    private final String meaning;

    /**
     * constructor
     * @param slang String
     * @param meaning String
     */
    public Slang(String slang, String meaning) {
        this.slang = slang;
        this.meaning = meaning;
    }

    public String getSlang() {
        return slang;
    }

    public String getMeaning() {
        return meaning;
    }

    @Override
    public String toString() {
        return slang;
    }
}
