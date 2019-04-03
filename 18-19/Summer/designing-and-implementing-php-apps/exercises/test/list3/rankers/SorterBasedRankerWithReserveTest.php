<?php

namespace list3\rankers;

use list3\sorters\DateSorter;
use list3\sorters\FirstNameSorter;
use list3\sorters\LastNameSorter;
use list3\sorters\PointsSorter;

class SorterBasedRankerWithReserveTest extends RankerTestCase {

    public function testRankByPointsAcceptedByPointsRejectedByLastNameReserveByLastName(): void {
        $ranker = new SorterBasedRankerWithReserve(new PointsSorter(), 2, 2);

        $ranker->prepareRanking($this->applications);
        $accepted = $ranker->getAcceptedStudents(new PointsSorter());
        $rejected = $ranker->getRejectedStudents(new LastNameSorter());
        $reserve = $ranker->getReserveStudents(new LastNameSorter());
        list($s1, $s2, $s3, $s4, $s5, $s6) = $this->students;

        self::assertEquals(array($s5, $s3), $accepted);
        self::assertEquals(array($s2, $s6), $reserve);
        self::assertEquals(array($s1, $s4), $rejected);
    }

    public function testRankByDateAcceptedByPointsRejectedByFirstNameReserveByFirstName(): void {
        $ranker = new SorterBasedRankerWithReserve(new DateSorter(), 2, 2);

        $ranker->prepareRanking($this->applications);
        $accepted = $ranker->getAcceptedStudents(new PointsSorter());
        $rejected = $ranker->getRejectedStudents(new FirstNameSorter());
        $reserve = $ranker->getReserveStudents(new FirstNameSorter());
        list($s1, $s2, $s3, $s4, $s5, $s6) = $this->students;

        self::assertEquals(array($s5, $s2), $accepted);
        self::assertEquals(array($s3, $s1), $reserve);
        self::assertEquals(array($s6, $s4), $rejected);
    }

}
