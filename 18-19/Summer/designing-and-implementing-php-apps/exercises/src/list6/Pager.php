<?php


namespace list6;


class Pager {

    /**
     * @var int
     */
    private $objectsCount;
    /**
     * @var int
     */
    private $objectsPerPage;
    /**
     * @var int
     */
    private $pagesOffset;
    /**
     * @var int
     */
    private $pagesRange;

    /**
     * Pager constructor.
     * @param int $objectsCount
     * @param int $objectsPerPage
     * @param int $objectsOffset
     * @param int $pagesRange
     */
    public function __construct(int $objectsCount, int $objectsPerPage, int $pagesRange) {
        $this->objectsCount = $objectsCount;
        $this->objectsPerPage = $objectsPerPage;
        $this->pagesRange = $pagesRange;
    }

    /**
     * @param int $offset
     * @throws PageIndexOutOfBoundsException
     */
    public function goToPage(int $offset) {
        if ($offset > $this->getPagesCount()) {
            throw new PageIndexOutOfBoundsException();
        }
        $this->pagesOffset = $offset;
    }

    public function getPagesCount(): int {
        return ceil($this->objectsCount / $this->objectsPerPage);
    }

    public function isPreviousLinkAvailable(): bool {
        return $this->pagesOffset > 1;
    }

    public function isNextLinkAvailable(): bool {
        return $this->pagesOffset < $this->getPagesCount();
    }

    public function getAvailablePages(): PagesList {
        $firstPages = $this->getFirstPages();
        $middlePages = $this->getMiddlePages();
        $lastPages = $this->getLastPages();

        if ($this->areNear($firstPages, $middlePages)) {
            $middlePages = $this->mergePages($firstPages, $middlePages);
            $firstPages = array();
        }

        if ($this->areNear($middlePages, $lastPages)) {
            $middlePages = $this->mergePages($middlePages, $lastPages);
            $lastPages = array();
        }

        return new PagesList($firstPages, $middlePages, $lastPages);

    }

    private function areNear(array $pages1, array $pages2) {
        return end($pages1) >= $pages2[0] - 1;
    }

    private function getFirstPages(): array {
        $start = 1;
        $end = min($this->getPagesCount(), $this->pagesRange);
        return range($start, $end);
    }

    private function getMiddlePages(): array {

        $start = max(1, $this->pagesOffset - $this->pagesRange);
        $end = min($this->getPagesCount(), $this->pagesOffset + $this->pagesRange);
        return range($start, $end);
    }

    private function getLastPages(): array {
        $start = max(1, $this->getPagesCount() - $this->pagesRange + 1);
        $end = $this->getPagesCount();
        return range($start, $end);
    }

    private function mergePages(array $pages1, array $pages2): array {
        $pages = array_merge($pages1, $pages2);
        return array_values(array_unique($pages));
    }


}