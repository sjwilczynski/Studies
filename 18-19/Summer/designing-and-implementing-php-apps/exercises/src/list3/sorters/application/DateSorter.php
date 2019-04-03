<?php


namespace list3\sorters\application;


use list3\Application;
use list3\sorters\ListSorter;

class DateSorter implements ListSorter {

    public function sort(array $list): array {
        $newList = $list;
        usort($newList, function (Application $application1, Application $application2) {
            $date1 = $application1->getSubmissionDate();
            $date2 = $application2->getSubmissionDate();
            return $date1 < $date2 ? -1 : $date1 == $date2 ? 0 : 1;
        });
        return $newList;
    }
}