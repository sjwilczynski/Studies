<?php


namespace list3\rankers;


use list3\sorters\ListSorter;
use list3\Student;

//for me it is not well defined point of change
interface ApplicationListRanker {


    public function prepareRanking(array $applications);

    /**
     * @return Student[]
     */
    public function getAcceptedStudents(ListSorter $sorter): array;

    /**
     * @return Student[]
     */
    public function getRejectedStudents(ListSorter $sorter): array;

}