import { useEffect, useMemo, useState } from 'react'
import { groupsApi } from '../api/groupsApi'
import { studentsApi } from '../api/studentsApi'
import { progressTree } from '../data/progressTree'
import { jsPDF } from 'jspdf'
import { Document, Packer, Paragraph, TextRun } from 'docx'
import { saveAs } from 'file-saver'
import { useAuthStore } from '../store/authStore'
import './MaterialsPage.css'

type StudentProgressView = {
  id: string
  name: string
  age: number
  inProgressByDomain: { domain: string; items: string[] }[]
}

type GroupProgressView = {
  id: string
  name: string
  description?: string
  students: StudentProgressView[]
}

const buildProgressMaps = () => {
  const itemTitleMap = new Map<string, string>()
  const itemDomainMap = new Map<string, string>()
  progressTree.forEach(domain => {
    domain.sections.forEach(section => {
      section.items.forEach(item => {
        itemTitleMap.set(item.id, item.title)
        itemDomainMap.set(item.id, `${domain.id}. ${domain.title}`)
      })
    })
  })
  return { itemTitleMap, itemDomainMap }
}

export default function MaterialsPage() {
  const [loading, setLoading] = useState(true)
  const [groupsData, setGroupsData] = useState<GroupProgressView[]>([])

  const { itemTitleMap, itemDomainMap } = useMemo(() => buildProgressMaps(), [])
  const today = useMemo(() => new Date(), [])
  const { user } = useAuthStore()

  useEffect(() => {
    const load = async () => {
      setLoading(true)
      try {
        const groupSummaries = await groupsApi.getGroups(true)
        const groupIds = groupSummaries.map((g) => ({ id: g.id, name: g.name }))
        const groups = await Promise.all(
          groupIds.map(async (g) => {
            const detail = await groupsApi.getGroupById(g.id)
            const students = await Promise.all(
              detail.students.map(async (student) => {
                const progress = await studentsApi.getStudentProgress(student.id)
                const inProgressItems = progress
                  .filter(p => p.status === 'IN_PROGRESS')
                  .map(p => ({
                    title: `${p.itemKey} ${itemTitleMap.get(p.itemKey) || ''}`.trim(),
                    domain: itemDomainMap.get(p.itemKey) || 'Other',
                  }))
                const byDomain = new Map<string, string[]>()
                inProgressItems.forEach(item => {
                  if (!byDomain.has(item.domain)) {
                    byDomain.set(item.domain, [])
                  }
                  byDomain.get(item.domain)?.push(item.title)
                })
                const inProgressByDomain = Array.from(byDomain.entries()).map(([domain, items]) => ({
                  domain,
                  items,
                }))
                return {
                  id: student.id,
                  name: student.name,
                  age: student.age,
                  inProgressByDomain,
                }
              })
            )
            return { id: g.id, name: g.name, description: detail.description, students }
          })
        )

        setGroupsData(groups)
      } catch (error) {
        console.error('Failed to load materials', error)
      } finally {
        setLoading(false)
      }
    }
    load()
  }, [itemTitleMap, itemDomainMap])

  const formatDate = (date: Date) => date.toLocaleDateString()

  const exportGroupPdf = (group: GroupProgressView) => {
    const doc = new jsPDF()
    doc.setFontSize(10)
    let y = 16
    const lineHeight = 7
    const notesLine = '__________________________________________________________________________________'
    const notesLine2 = '____________________________________________________________________________________'

    const addLine = (text: string) => {
      if (y > 280) {
        doc.addPage()
        y = 16
      }
      doc.text(text, 14, y)
      y += lineHeight
    }

    addLine(`Group name: ${group.name}`)
    addLine(`Teacher name: ${user?.fullName || '—'}`)
    addLine(`Date: ${formatDate(today)}`)
    addLine(`Goal: ${group.description || '—'}`)
    addLine('')

    group.students.forEach((student) => {
      addLine(`Student: ${student.name} (Age ${student.age})`)
      if (student.inProgressByDomain.length === 0) {
        addLine('Domains: None')
      } else {
        student.inProgressByDomain.forEach((domain) => {
          addLine(`${domain.domain}:`)
          domain.items.forEach((item) => {
            addLine(`   ${item}`)
          })
        })
      }
      addLine(`Notes: ${notesLine}`)
      addLine(`       ${notesLine2}`)
      addLine(`       ${notesLine2}`)
      addLine('')
    })

    doc.save(`summary-${group.name}-${formatDate(today)}.pdf`)
  }

  const exportGroupDocx = async (group: GroupProgressView) => {
    const paragraphs: Paragraph[] = []
    const notesLine = '__________________________________________________________________________________'
    const notesLine2 = '___________________________________________________________________________________'
    paragraphs.push(new Paragraph({ children: [new TextRun({ text: `Group name: ${group.name}`, bold: true, size: 20 })] }))
    paragraphs.push(new Paragraph({ children: [new TextRun({ text: `Teacher name: ${user?.fullName || '—'}`, size: 20 })] }))
    paragraphs.push(new Paragraph({ children: [new TextRun({ text: `Date: ${formatDate(today)}`, size: 20 })] }))
    paragraphs.push(new Paragraph({ children: [new TextRun({ text: `Goal: ${group.description || '—'}`, size: 20 })] }))
    paragraphs.push(new Paragraph({ text: '' }))

    group.students.forEach((student) => {
      paragraphs.push(new Paragraph({ children: [new TextRun({ text: `Student: ${student.name} (Age ${student.age})`, bold: true, size: 20 })] }))
      if (student.inProgressByDomain.length === 0) {
        paragraphs.push(new Paragraph({ children: [new TextRun({ text: 'Domains: None', size: 20 })] }))
      } else {
        student.inProgressByDomain.forEach((domain) => {
          paragraphs.push(new Paragraph({ children: [new TextRun({ text: domain.domain, size: 20, bold: true })] }))
          domain.items.forEach((item) => {
            paragraphs.push(new Paragraph({ children: [new TextRun({ text: `- ${item}`, size: 20 })] }))
          })
        })
      }
      paragraphs.push(new Paragraph({ children: [new TextRun({ text: `Notes: ${notesLine}`, size: 20 })] }))
      paragraphs.push(new Paragraph({ children: [new TextRun({ text: `       ${notesLine2}`, size: 20 })] }))
      paragraphs.push(new Paragraph({ children: [new TextRun({ text: `       ${notesLine2}`, size: 20 })] }))
      paragraphs.push(new Paragraph({ text: '' }))
    })

    const doc = new Document({ sections: [{ children: paragraphs }] })
    const blob = await Packer.toBlob(doc)
    saveAs(blob, `summary-${group.name}-${formatDate(today)}.docx`)
  }

  return (
    <div className="materials-page">
      <div className="materials-header">
        <h2>Materials</h2>
        <div className="materials-date">Date: {formatDate(today)}</div>
      </div>

      {loading ? (
        <div className="loading">Loading...</div>
      ) : groupsData.length === 0 ? (
        <div className="empty-state">No groups available.</div>
      ) : (
        <div className="materials-groups">
          <div className="materials-list-header">Groups Import</div>
          {groupsData.map((group) => (
            <div key={group.id} className="materials-group-card compact">
              <div className="materials-group-header">
                <div className="materials-group-title">{group.name}</div>
                <div className="materials-actions">
                  <button className="materials-btn" onClick={() => exportGroupPdf(group)}>
                    Export PDF
                  </button>
                  <button className="materials-btn" onClick={() => exportGroupDocx(group)}>
                    Export DOCX
                  </button>
                </div>
              </div>
              <div className="materials-group-goal">Goal: {group.description || '—'}</div>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}
