package eu.mnrdesign.matned.final_project.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Complicity {
    EASY,
    MEDIUM,
    DIFFICULT,
    IMPOSSIBLE;

    public static List<String> getList(){
        return Arrays.stream(Complicity.values()).map(Enum::name).collect(Collectors.toList());
    }

}
