<?php

namespace list3\rankers;


use list3\sorters\DateSorter;
use list3\sorters\FirstNameSorter;
use list3\sorters\LastNameSorter;
use list3\sorters\PointsSorter;

class SorterBasedRankerTest extends RankerTestCase {

    public function testRankByPointsAcceptedByPointsRejectedByLastName(): void {
        $ranker = new SorterBasedRanker(new PointsSorter(), 3);

        $ranker->prepareRanking($this->applications);
        $accepted = $ranker->getAcceptedStudents(new PointsSorter());
        $rejected = $ranker->getRejectedStudents(new LastNameSorter());
        list($s1, $s2, $s3, $s4, $s5, $s6) = $this->students;

        self::assertEquals(array($s5, $s3, $s6), $accepted);
        self::assertEquals(array($s1, $s4, $s2), $rejected);
    }

    public function testRankByDateAcceptedByPointsRejectedByFirstName(): void {
        $ranker = new SorterBasedRanker(new DateSorter(), 3);

        $ranker->prepareRanking($this->applications);
        $accepted = $ranker->getAcceptedStudents(new PointsSorter());
        $rejected = $ranker->getRejectedStudents(new FirstNameSorter());
        list($s1, $s2, $s3, $s4, $s5, $s6) = $this->students;

        self::assertEquals(array($s5, $s2, $s1), $accepted);
        self::assertEquals(array($s3, $s6, $s4), $rejected);
    }

}
