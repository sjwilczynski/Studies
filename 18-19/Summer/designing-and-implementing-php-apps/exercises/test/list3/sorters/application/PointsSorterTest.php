<?php

namespace list3\sorters\application;

use list3\sorters\SorterTestCase;

class PointsSorterTest extends SorterTestCase {

    public function testPointsSorter(): void {
        $sorter = new PointsSorter();
        list($a1, $a2, $a3, $a4) = $this->applications;
        self::assertEquals(array($a4, $a1, $a2, $a3), $sorter->sort($this->applications));
    }

}
