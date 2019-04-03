<?php

namespace list3\sorters;

class PointsSorterTest extends SorterTestCase {

    public function testPointsSorter(): void {
        $sorter = new PointsSorter();
        list($a1, $a2, $a3, $a4) = $this->applications;
        self::assertEquals(array($a3, $a2, $a1, $a4), $sorter->sort($this->applications));
    }

}
