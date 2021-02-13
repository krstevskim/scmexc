import {ItemType} from "./item-type.enum";
import {Question} from "./question.interface";

export interface Item {
  id: number;
  type: ItemType;
  question: Question;
}
