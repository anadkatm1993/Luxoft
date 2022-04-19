package com.task.model;

import java.util.List;

public class Result {
    private List<String> vowels;
    private int count;
    private int times;
    private int length;

    public List<String> getVowels() {
        return vowels;
    }

    public void setVowels(List<String> vowels) {
        this.vowels = vowels;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Result{");
        sb.append("vowels=").append(vowels);
        sb.append(", count=").append(count);
        sb.append(", times=").append(times);
        sb.append(", length=").append(length);
        sb.append('}');
        return sb.toString();
    }
}
