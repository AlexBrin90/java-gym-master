package ru.yandex.practicum.gym;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TimetableTest {

    @Test
    public void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, mondaySessions.size());
        assertEquals(singleTrainingSession, mondaySessions.get(0));

        List<TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertTrue(tuesdaySessions.isEmpty());
    }

    @Test
    public void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, mondaySessions.size());

        List<TrainingSession> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        assertEquals(2, thursdaySessions.size());
        assertEquals(new TimeOfDay(13, 0), thursdaySessions.get(0).getTimeOfDay());
        assertEquals(new TimeOfDay(20, 0), thursdaySessions.get(1).getTimeOfDay());

        List<TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertTrue(tuesdaySessions.isEmpty());
    }

    @Test
    public void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> sessionsAt13 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        assertEquals(1, sessionsAt13.size());
        assertEquals(singleTrainingSession, sessionsAt13.get(0));

        List<TrainingSession> sessionsAt14 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        assertTrue(sessionsAt14.isEmpty());
    }

    @Test
    public void testMultipleSessionsAtSameTime() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Измайлов", "Константин", "Викторович");
        Coach coach2 = new Coach("Семёнов", "Виктор", "Константинович");

        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);
        Group group2 = new Group("Йога для детей", Age.CHILD, 45);

        TrainingSession session1 = new TrainingSession(group1, coach1,
                DayOfWeek.WEDNESDAY, new TimeOfDay(18, 0));
        TrainingSession session2 = new TrainingSession(group2, coach2,
                DayOfWeek.WEDNESDAY, new TimeOfDay(18, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.WEDNESDAY, new TimeOfDay(18, 0));
        assertEquals(2, sessions.size());
    }

    @Test
    public void testEmptyDayReturnsEmptyList() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession session = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        timetable.addNewTrainingSession(session);

        List<TrainingSession> fridaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.FRIDAY);
        assertTrue(fridaySessions.isEmpty());

        List<TrainingSession> fridayTimeSessions = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.FRIDAY, new TimeOfDay(10, 0));
        assertTrue(fridayTimeSessions.isEmpty());
    }

    @Test
    public void testSessionsSortedByTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для взрослых", Age.ADULT, 90);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        TrainingSession session1 = new TrainingSession(group, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(20, 0));
        TrainingSession session2 = new TrainingSession(group, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(10, 0));
        TrainingSession session3 = new TrainingSession(group, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(15, 30));
        TrainingSession session4 = new TrainingSession(group, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(10, 30));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);
        timetable.addNewTrainingSession(session3);
        timetable.addNewTrainingSession(session4);

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDay(DayOfWeek.FRIDAY);

        assertEquals(4, sessions.size());
        assertEquals(new TimeOfDay(10, 0), sessions.get(0).getTimeOfDay());
        assertEquals(new TimeOfDay(10, 30), sessions.get(1).getTimeOfDay());
        assertEquals(new TimeOfDay(15, 30), sessions.get(2).getTimeOfDay());
        assertEquals(new TimeOfDay(20, 0), sessions.get(3).getTimeOfDay());
    }

    @Test
    public void testGetCountByCoachesMultipleCoaches() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Измайлов", "Константин", "Викторович");
        Coach coach3 = new Coach("Семёнов", "Виктор", "Константинович");

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);

        for (int i = 0; i < 5; i++) {
            timetable.addNewTrainingSession(new TrainingSession(group, coach1,
                    DayOfWeek.values()[i % DayOfWeek.values().length], new TimeOfDay(10, 0)));
        }

        for (int i = 0; i < 3; i++) {
            timetable.addNewTrainingSession(new TrainingSession(group, coach2,
                    DayOfWeek.values()[i % DayOfWeek.values().length], new TimeOfDay(11, 0)));
        }

        timetable.addNewTrainingSession(new TrainingSession(group, coach3,
                DayOfWeek.MONDAY, new TimeOfDay(12, 0)));

        List<CounterOfTrainings> result = timetable.getCountByCoaches();

        assertEquals(3, result.size());
        assertEquals(coach1, result.get(0).getCoach());
        assertEquals(5, result.get(0).getCount());
        assertEquals(coach2, result.get(1).getCoach());
        assertEquals(3, result.get(1).getCount());
        assertEquals(coach3, result.get(2).getCoach());
        assertEquals(1, result.get(2).getCount());
    }

    @Test
    public void testGetCountByCoachesEmptyTimetable() {
        Timetable timetable = new Timetable();
        List<CounterOfTrainings> result = timetable.getCountByCoaches();
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetCountByCoachesSingleCoach() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);

        for (int i = 0; i < 10; i++) {
            timetable.addNewTrainingSession(new TrainingSession(group, coach,
                    DayOfWeek.values()[i % DayOfWeek.values().length], new TimeOfDay(10, 0)));
        }

        List<CounterOfTrainings> result = timetable.getCountByCoaches();

        assertEquals(1, result.size());
        assertEquals(coach, result.get(0).getCoach());
        assertEquals(10, result.get(0).getCount());
    }

    @Test
    public void testGetCountByCoachesEqualCounts() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Измайлов", "Константин", "Викторович");

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);

        timetable.addNewTrainingSession(new TrainingSession(group, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, coach2,
                DayOfWeek.TUESDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach2,
                DayOfWeek.THURSDAY, new TimeOfDay(10, 0)));

        List<CounterOfTrainings> result = timetable.getCountByCoaches();

        assertEquals(2, result.size());
        assertEquals(2, result.get(0).getCount());
        assertEquals(2, result.get(1).getCount());
    }
}