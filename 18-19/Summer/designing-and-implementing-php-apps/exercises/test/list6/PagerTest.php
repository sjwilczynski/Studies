<?php

namespace list6;

use PHPUnit\Framework\TestCase;

class PagerTest extends TestCase {

    /**
     * @throws PageIndexOutOfBoundsException
     */
    public function testIndexOutOfBounds() {
        $this->expectException(PageIndexOutOfBoundsException::class);
        $this->testPager(10, 5, 1, 3, 2, false, false, new PagesList(array(), array(), array()));
    }


    /**
     * @dataProvider pagerProvider
     * @throws PageIndexOutOfBoundsException
     */
    public function testPager(int $objectsCount, int $objectsPerPage, int $pagesRange, int $pageNumber, int $pagesCount,
                              bool $isNextAvailable, bool $isPreviousAvailable, PagesList $pagesList) {

        $pager = new Pager($objectsCount, $objectsPerPage, $pagesRange);

        $pager->goToPage($pageNumber);

        self::assertEquals($pagesCount, $pager->getPagesCount());
        self::assertEquals($isNextAvailable, $pager->isNextLinkAvailable());
        self::assertEquals($isPreviousAvailable, $pager->isPreviousLinkAvailable());
        self::assertEquals($pagesList, $pager->getAvailablePages());
    }


    public function pagerProvider() {
        return [
            'current page is first' => [16, 5, 1, 1, 4, true, false, new PagesList(array(), array(1, 2), array(4))],
            'current page is last' => [16, 5, 1, 4, 4, false, true, new PagesList(array(1), array(3, 4), array())],
            'current page is near first' => [28, 5, 1, 2, 6, true, true, new PagesList(array(), array(1, 2, 3), array(6))],
            'current page is near last' => [28, 5, 1, 5, 6, true, true, new PagesList(array(1), array(4, 5, 6), array())],
            'current page is far' => [32, 5, 1, 4, 7, true, true, new PagesList(array(1), array(3, 4, 5), array(7))],
            'bigger range' => [72, 5, 2, 7, 15, true, true, new PagesList(array(1, 2), array(5, 6, 7, 8, 9), array(14, 15))],
            'current page is far but previous page near first' => [32, 5, 1, 3, 7, true, true, new PagesList(array(), array(1, 2, 3, 4), array(7))],
            'current page is far but next page near last' => [32, 5, 1, 5, 7, true, true, new PagesList(array(1), array(4, 5, 6, 7), array())],
            'only one page' => [4, 5, 1, 1, 1, false, false, new PagesList(array(), array(1), array())],
            'range bigger than number of pages' => [10, 3, 5, 2, 4, true, true, new PagesList(array(), array(1, 2, 3, 4), array())]
        ];
    }

}
