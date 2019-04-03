<?php

namespace list3\sorters;


class LastNameSorterTest extends SorterTestCase {


    public function testLastNameSorter(): void {
        $sorter = new LastNameSorter();
        list($a1, $a2, $a3, $a4) = $this->applications;
        self::assertEquals(array($a1, $a4, $a2, $a3), $sorter->sort($this->applications));
    }

}