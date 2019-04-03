<?php


namespace list3\rankers;


use list3\Application;
use list3\sorters\ListSorter;

class SorterBasedRanker implements ApplicationListRanker {

    /**
     * @var ListSorter
     */
    protected $sorter;
    /**
     * @var int
     */
    protected $numberOfAcceptedCandidates;

    /**
     * @var array $applications
     */
    protected $groupedApplications;

    /**
     * Ranker constructor.
     * @param ListSorter $sorter
     * @param int $numberOfAcceptedCandidates
     */
    public function __construct(ListSorter $sorter, int $numberOfAcceptedCandidates) {
        $this->sorter = $sorter;
        $this->numberOfAcceptedCandidates = $numberOfAcceptedCandidates;
    }

    public function prepareRanking(array $applications) {
        $rankedApplications = $this->sorter->sort($applications);

        $this->groupedApplications = array(array_slice($rankedApplications, 0, $this->numberOfAcceptedCandidates),
            array_slice($rankedApplications, $this->numberOfAcceptedCandidates));
    }

    /**
     * @return Application[]
     */
    public function getAcceptedApplications(): array {
        return $this->groupedApplications[0];
    }

    /**
     * @return Application[]
     */
    public function getRejectedApplications(): array {
        return $this->groupedApplications[1];
    }
}