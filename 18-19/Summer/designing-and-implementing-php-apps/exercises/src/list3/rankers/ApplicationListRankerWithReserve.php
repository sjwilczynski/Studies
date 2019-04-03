<?php


namespace list3\rankers;

use list3\sorters\ListSorter;
use list3\Student;

interface ApplicationListRankerWithReserve extends ApplicationListRanker {

    /**
     * @return Student[]
     */
    public function getReserveStudents(ListSorter $sorter): array;
}