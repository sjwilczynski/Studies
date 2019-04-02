<?php

namespace list3\sorters\student;


use list3\sorters\SorterTestCase;

class FirstNameSorterTest extends SorterTestCase {


    public function testFirstNameSorter(): void {
        $sorter = new FirstNameSorter();
        list($s1, $s2, $s3, $s4) = $this->students;
        self::assertEquals(array($s3, $s1, $s2, $s4), $sorter->sort($this->students));
    }

}
