<?php

namespace list3\sorters;

class DateSorterTest extends SorterTestCase {

    public function testDateSorter(): void {
        $sorter = new DateSorter();
        list($a1, $a2, $a3, $a4) = $this->applications;
        self::assertEquals(array($a2, $a1, $a3, $a4), $sorter->sort($this->applications));
    }

}
