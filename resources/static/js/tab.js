const tabMenus = document.querySelectorAll('.tab__menu-item');
const tabStorageKey = 'activeTab';

tabMenus.forEach((tabMenu) => {
    tabMenu.addEventListener('click', (e) => {
        e.preventDefault();
        const tabTargetData = e.currentTarget.dataset.tab;
        tabSwitch(e, tabTargetData);
        localStorage.setItem(tabStorageKey, tabTargetData);
    });
});

document.addEventListener('DOMContentLoaded', () => {
    const storedTab = localStorage.getItem(tabStorageKey);
    
    
const defaultTab = storedTab || '01';
    const defaultTabMenu = document.querySelector(`.tab__menu-item[data-tab="${defaultTab}"]`);

    if (defaultTabMenu) {
        defaultTabMenu.click();
    }
});


    
function tabSwitch(e, tabTargetData) {
    const tabList = e.currentTarget.closest('.tab__menu');
    const tabItems = tabList.querySelectorAll('.tab__menu-item');
    const tabPanelItems = tabList.nextElementSibling.querySelectorAll('.tab__panel-box');

    tabItems.forEach((tabItem) => {
        tabItem.classList.remove('is-active');
    });
    tabPanelItems.forEach((tabPanelItem) => {
        tabPanelItem.classList.remove('is-show');
    });

    e.currentTarget.classList.add('is-active');
    tabPanelItems.forEach((tabPanelItem) => {
        if (tabPanelItem.dataset.panel === tabTargetData) {
            tabPanelItem.classList.add('is-show');
        
            
         
localStorage.setItem(tabStorageKey, tabTargetData);
            
            
 
updatePaginationLinks(tabPanelItem, tabTargetData);
        }
    });
}


function updatePaginationLinks(activeTabPanel, activeTab) {
    
let paginationLinks;

    

    
if (activeTab === '01') {
        paginationLinks =Array.from(activeTabPanel.querySelectorAll('.nagoyameshi-page-link1'));
    } else if (activeTab === '02') {
        paginationLinks = Array.from(activeTabPanel.querySelectorAll('.nagoyameshi-page-link2'));
    } 
   
else {
        return;
    }

    paginationLinks.forEach((link, index) => {
        
      
const page = index + 1;

        

        

        
    
const storedTab = localStorage.getItem(tabStorageKey);
        
        
        

        const shouldDisplay = storedTab === activeTab && page <= totalPages; 

        if (shouldDisplay) {
            link.href = `/reservation?page=${page}&tab=${activeTab}`;
            
     
console.log(`Updated link${activeTab} ${index + 1}: ${link.href}`);
        } 
 
else {
            link.removeAttribute('href');
        }
    });
}
