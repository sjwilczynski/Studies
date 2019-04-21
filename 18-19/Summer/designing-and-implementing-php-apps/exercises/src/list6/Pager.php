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
    private $objectsOffset;
    /**
     * @var int
     */
    private $pagesRange;
    /**
     * @var int
     */
    private $lastPage;

    /**
     * Pager constructor.
     * @param int $objectsCount
     * @param int $objectsPerPage
     * @param int $objectsOffset
     * @param int $pagesRange
     */
    public function __construct(int $objectsCount, int $objectsPerPage, int $objectsOffset, int $pagesRange) {
        $this->objectsCount = $objectsCount;
        $this->objectsPerPage = $objectsPerPage;
        $this->objectsOffset = $objectsOffset;
        $this->pagesRange = $pagesRange;
    }


    public function getPagesCount(): int {
        return 0;
    }

    public function isNextLinkAvailable(): bool {
        return false;
    }

    public function isPreviousLinkAvailable(): bool {
        return false;
    }

    public function getAvailablePages(): PagesList {
        return null;
    }


}