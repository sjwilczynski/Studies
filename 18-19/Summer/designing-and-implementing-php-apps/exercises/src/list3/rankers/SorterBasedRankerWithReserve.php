<?php


namespace list3\rankers;


use list3\Application;
use list3\ListSorter;

class SorterBasedRankerWithReserve extends SorterBasedRanker implements ApplicationListRankerWithReserve {

    /**
     * @var int
     */
    protected $numberOfReserveCandidates;

    public function __construct(ListSorter $sorter, int $numberOfAcceptedCandidates, int $numberOfReserveCandidates) {
        parent::__construct($sorter, $numberOfAcceptedCandidates);
        $this->numberOfReserveCandidates = $numberOfReserveCandidates;
    }

    public function prepareRanking(array $applications) {
        $rankedApplications = $this->sorter->sort($applications);

        $this->groupedApplications = array(array_slice($rankedApplications, 0, $this->numberOfAcceptedCandidates),
            array_slice($rankedApplications, $this->numberOfAcceptedCandidates + $this->numberOfReserveCandidates),
            array_slice($rankedApplications, $this->numberOfAcceptedCandidates, $this->numberOfReserveCandidates));
    }

    /**
     * @return Application[]
     */
    public function getReserveApplications(): array {
        return $this->groupedApplications[2];
    }
}