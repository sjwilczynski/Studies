<?php

namespace list3\sorters\student;


use list3\sorters\SorterTestCase;

class LastNameSorterTest extends SorterTestCase {


    public function testLastNameSorter(): void {
        $sorter = new LastNameSorter();
        list($s1, $s2, $s3, $s4) = $this->students;
        self::assertEquals(array($s1, $s4, $s2, $s3), $sorter->sort($this->students));
    }

}