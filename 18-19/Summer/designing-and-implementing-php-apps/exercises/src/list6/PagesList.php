<?php


namespace list6;


class PagesList {

    /**
     * @var array
     */
    private $firstPages;
    /**
     * @var array
     */
    private $middlePages;
    /**
     * @var array
     */
    private $lastPages;

    /**
     * PagesList constructor.
     * @param array $firstPages
     * @param array $middlePages
     * @param array $lastPages
     */
    public function __construct(array $firstPages, array $middlePages, array $lastPages) {
        $this->firstPages = $firstPages;
        $this->middlePages = $middlePages;
        $this->lastPages = $lastPages;
    }

    /**
     * @return array
     */
    public function getFirstPages(): array {
        return $this->firstPages;
    }

    /**
     * @return array
     */
    public function getMiddlePages(): array {
        return $this->middlePages;
    }

    /**
     * @return array
     */
    public function getLastPages(): array {
        return $this->lastPages;
    }
}