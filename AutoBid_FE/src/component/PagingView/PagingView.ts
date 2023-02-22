import Component from "../../core/component";
import {rangeFromTo} from "../../core/util";
import {selectPage} from "../../store/query";
import "./pagingview.css";

class PagingView extends Component<any,
    { currentPage: number, numberOfPages: number, pagesPerShow: number }>
{
    template(): InnerHTML["innerHTML"] {
        const { currentPage, numberOfPages, pagesPerShow } = this.props;

        let firstPage = currentPage - currentPage % pagesPerShow;
        let lastPage = currentPage - currentPage % pagesPerShow + pagesPerShow - 1;
        if (firstPage < 1)
            firstPage = 1;
        if (lastPage > numberOfPages)
            lastPage = numberOfPages;

        return `
        ${firstPage !== 1 ? 
            `<button class="pagination__btn" data-page="${firstPage - 1}"><</button>` : ''}
        ${rangeFromTo(firstPage, lastPage + 1).map(page => `
            <button class="pagination__btn ${page === currentPage ? 'pagination__btn--selected' : ''}" 
                data-page="${page}">${page}</button>
        `).join('')}
        ${lastPage !== numberOfPages ? 
            `<button class="pagination__btn" data-page="${lastPage + 1}">></button>` : ''}
        `
    }

    pageButtonEvent(e: Event) {
        const $pageButton = (e.target as Element).closest('.pagination__btn') as HTMLElement;
        const page = $pageButton.dataset.page as string;
        selectPage(parseInt(page));
    }

    initialize() {
        this.addEvent('click', '.pagination__btn', this.pageButtonEvent.bind(this));
    }
}

export default PagingView;