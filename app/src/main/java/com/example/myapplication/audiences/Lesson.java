package com.example.myapplication.audiences;

import org.joda.time.Interval;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Lesson {
    private int id;
    private String teacherName, teacherSurname;
    private String subjectName;
    private Interval interval;
    private int audNumber;

    public Lesson(String teacherName, String teacherSurname, String subjectName, Interval interval, int audNumber) {
        this.teacherName = teacherName;
        this.teacherSurname = teacherSurname;
        this.subjectName = subjectName;
        this.interval = interval;
        this.audNumber = audNumber;
    }

    public static ArrayList<Lesson> getLessonsByAudienceAndDay(List<Lesson> lessonsList, int audience, int day_numb) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        for(Lesson lesson: lessonsList){
            if(lesson.getInterval().getStart().getDayOfWeek() == day_numb && lesson.getAudNumber() == audience){
                lessons.add(lesson);
            }
        }
        return lessons;
    }

    public static ArrayList<String> getTeachersList(List<Lesson> lessonsList) {
        Set<String> teachers = new TreeSet<>();
        for(Lesson lesson: lessonsList){
                teachers.add(lesson.getTeacherName());
        }
        return new ArrayList<>(teachers);
    }

    public static ArrayList<Lesson> getLessonsByTeacherName(List<Lesson> lessonsList, String name) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        for (Lesson lesson: lessonsList){
            if(lesson.getTeacherName().equals(name)){
                lessons.add(lesson);
            }
        }
        return lessons;
    }

    public static List<String> getUniqueSubjects(List<Lesson> lessons) {
        Set<String> subjects = new HashSet<>();
        for(Lesson lesson: lessons){
            subjects.add(lesson.getSubjectName());
        }
        return new ArrayList<>(subjects);
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherSurname() {
        return teacherSurname;
    }

    public void setTeacherSurname(String teacherSurname) {
        this.teacherSurname = teacherSurname;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    public int getAudNumber() {
        return audNumber;
    }

    public void setAudNumber(int audNumber) {
        this.audNumber = audNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", teacherName='" + teacherName + '\'' +
                ", teacherSurname='" + teacherSurname + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", interval=" + interval +
                ", audNumber=" + audNumber +
                '}';
    }

    static ArrayList<Lesson> getAllLessonsByAudience(List<Lesson> lessonsList, int audNumber) {
        ArrayList<Lesson> lessonsByAud = new ArrayList<>();
        for(Lesson lesson: lessonsList){
            if(lesson.getAudNumber() == audNumber){
                lessonsByAud.add(lesson);
            }
        }
        return lessonsByAud;
    }
}
