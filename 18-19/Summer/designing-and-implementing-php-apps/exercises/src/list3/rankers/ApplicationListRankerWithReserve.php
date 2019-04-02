<?php


namespace list3\rankers;

use list3\Application;

interface ApplicationListRankerWithReserve extends ApplicationListRanker {

    /**
     * @return Application[]
     */
    public function getReserveApplications() : array;
}