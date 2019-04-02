<?php


namespace list3\rankers;


use list3\Application;

interface ApplicationListRanker {


    public function prepareRanking(array $applications);
    /**
     * @return Application[]
     */
    public function getAcceptedApplications() : array;
    /**
     * @return Application[]
     */
    public function getRejectedApplications() : array;

}