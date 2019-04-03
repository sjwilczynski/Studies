<?php

namespace list3\sorters\application;




use list3\Application;
use list3\sorters\ListSorter;

class PointsSorter implements ListSorter {

    public function sort(array $list): array {
        $newList = $list;
        usort($newList, function (Application $application1, Application $application2) {
            return $application1->getPoints() - $application2->getPoints();
        });
        return $newList;
    }
}