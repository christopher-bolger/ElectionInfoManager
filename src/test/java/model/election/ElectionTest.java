package model.election;

import electionInfoManager.model.election.Election;
import electionInfoManager.model.election.ElectionEntry;
import electionInfoManager.model.election.Politician;
import electionInfoManager.model.linkedlist.LinkedList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ElectionTest {
    Politician p1, p2, p3, p4, p5, p6, p7, p8;
    Election e1, e2, e3, e4, e5, e6, e7;
    LinkedList<Politician> politicians;

    @BeforeEach
    void setUp() {
        p1 = new Politician("Micheal Martin", "Fianna Fail", "Cork", "https://upload.wikimedia.org/wikipedia/commons/7/7e/Micheal_Martin_2020.jpg", LocalDate.of(1960, 8, 16));
        p2 = new Politician("Leo Varadkar", "Fianna Gael", "Dublin", "https://upload.wikimedia.org/wikipedia/commons/3/3d/Leo_Varadkar_2022.jpg", LocalDate.of(1979, 1, 18));
        p3 = new Politician("Mary Lou McDonald", "Sinn Fein", "Dublin", "https://upload.wikimedia.org/wikipedia/commons/5/5f/Mary_Lou_McDonald_2023.jpg", LocalDate.of(1969, 5, 1));
        p4 = new Politician("Eamon Ryan", "Green Party", "Dublin", "https://upload.wikimedia.org/wikipedia/commons/6/6b/Eamon_Ryan_2021.jpg", LocalDate.of(1963, 7, 28));
        p5 = new Politician("Ivana Bacik", "Labour Party", "Dublin", "https://upload.wikimedia.org/wikipedia/commons/8/8a/Ivana_Bacik_2022.jpg", LocalDate.of(1968, 5, 25));
        p6 = new Politician("Richard Boyd Barrett", "People Before Profit", "Dublin", "https://upload.wikimedia.org/wikipedia/commons/4/4c/Richard_Boyd_Barrett_2016.jpg", LocalDate.of(1967, 2, 6));
        p7 = new Politician("Peadar Toibin", "Aontu", "Meath", "https://upload.wikimedia.org/wikipedia/commons/2/2b/Peadar_Toibin_2019.jpg", LocalDate.of(1974, 6, 1));

        politicians = new LinkedList<>();
        politicians.add(p1);
        politicians.add(p2);
        politicians.add(p3);
        politicians.add(p4);
        politicians.add(p5);
        politicians.add(p6);
        politicians.add(p7);

        p8 = new Politician("Holly Cairns", "Social Democrats", "Cork", "https://upload.wikimedia.org/wikipedia/commons/9/9a/Holly_Cairns_2023.jpg", LocalDate.of(1989, 11, 4));

        //public Election(int type, LocalDate date, int winners, String location)
        e1 = new Election("Presidential", LocalDate.of(2025, 10, 12), 1, "Dublin");
        e1.add(p1, 10000);
        e1.add(p2, 9643);
        e1.add(p3, 192);
        e1.add(p4, 1200);
        e1.add(p5, 7392);
        e1.add(p6, 9462);
        e1.add(p7, 100);

        e2 = new Election("General", LocalDate.of(2020, 1, 31), 4, "Dublin");
        e2.add(p1, 9264);
        e2.add(p2, 9643);
        e2.add(p3, 2495);
        e2.add(p4, 7482);
        e2.add(p5, 7392);
        e2.add(p6, 9462);
        e2.add(p7, 5579);
    }

    @AfterEach
    void tearDown() {
        p1 = p2 = p3 = p4 = p5 = p6 = p7 = null;
        e1 = e2 = e3 = e4 = e5 = e6 = e7 = null;
    }

    @Test
    void sortByVotes() {
        e1.sortByVotes();
        assertEquals(10000, e1.getCandidates().getFirst().getVotes());
        assertEquals(100, e1.getCandidates().getLast().getVotes());
        e2.sortByVotes();
        assertEquals(9643, e2.getCandidates().getFirst().getVotes());
        assertEquals(2495, e2.getCandidates().getLast().getVotes());
    }

    @Test
    void getCandidates() {
        LinkedList<ElectionEntry> candidates = e1.getCandidates();
        for(ElectionEntry e : candidates) {
            assertTrue(politicians.contains(e.getPolitician()));
        }
        assertEquals(politicians.size(), candidates.size());
    }

    @Test
    void getWinners() {
        LinkedList<ElectionEntry> winners = e1.calculateResults();
        assertEquals(winners.getFirst().getPolitician(), p1);
        assertEquals(1, winners.size()); //e1 is a presidential election

//        e2.add(p1, 9264);
//        e2.add(p2, 9643);
//        e2.add(p3, 2495);
//        e2.add(p4, 7482);
//        e2.add(p5, 7392);
//        e2.add(p6, 9462);
//        e2.add(p7, 5579);
        winners = e2.calculateResults();
        assertEquals(winners.getFirst().getPolitician(), p2);
        assertEquals(winners.get(1).getPolitician(), p6);
        assertEquals(winners.get(2).getPolitician(), p1);
        assertEquals(winners.getLast().getPolitician(), p4);
        assertEquals(4, winners.size()); //e2 is a general with 3 seats
    }

    @Test
    void add() {
        assertEquals(7, e1.getCandidates().size());
        e1.add(p8, 10);
        assertEquals(8, e1.getCandidates().size());
    }

    @Test
    void getAffiliation() {
        p1.setAffiliation("Affiliation");
        assertEquals("Fianna Fail", e1.getAffiliation(p1));
    }

    @Test
    void getPolitician() {
        assertEquals(p1, e1.getPolitician(new ElectionEntry(p1)));
    }
}