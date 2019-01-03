package  moe.xinmu.android.ahwork.utils;

import java.util.Objects;

public class StringUtil{
    public static java.lang.String join(CharSequence delimiter, CharSequence... elements) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(elements);
        StringJoiner joiner = new StringJoiner(delimiter);
        for (CharSequence cs: elements)
            joiner.add(cs);
        return joiner.toString();
    }
}