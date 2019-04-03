<?php

namespace list3\sorters;


class FirstNameSorterTest extends SorterTestCase {


    public function testFirstNameSorter(): void {
        $sorter = new FirstNameSorter();
        list($a1, $a2, $a3, $a4) = $this->applications;
        self::assertEquals(array($a3, $a1, $a2, $a4), $sorter->sort($this->applications));
    }

}
